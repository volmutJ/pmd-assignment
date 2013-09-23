/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.nullpointerassignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;

/**
 *
 * @author honzaq
 */
public class BugPatternDetectorImpl implements BugPatternDetector, Constants2 {

    private ArrayList<Item> stack;
    private Map<Integer, Item> itemsPool;
    private InstructionList instructionsPool;

    public BugPatternDetectorImpl() {
        this.stack = new ArrayList<>();
        this.itemsPool = new HashMap<>();
    }

    /**
     * Manages the classes behavior. It receives list of instructions and then
     * run main branch of execution from index 0 (root)
     *
     * @param fullyQualifiedClassName
     * @param methodName
     * @return
     */
    @Override
    public boolean methodContainsBugPattern(
            String fullyQualifiedClassName, String methodName) {

        InstructionList handlers = this.provideHandlersToMethod(
                fullyQualifiedClassName, methodName);
        
        this.instructionsPool = handlers;
        boolean result = false;
        
        try {
            this.runInstructions(0);
        } catch (DangerousPatternException e) {
            result = true;
        }

        return result;
    }

    /**
     * Runs through instructions from given index which is important when
     * executing branch of conditions. Also the stack and item pool are cloned
     * and put back afterwards.
     *
     * @param startInstruction
     * @return true - method is dangerous; false - method is safe
     * @throws DangerousPatternException
     */
    private boolean runInstructions(int startInstruction)
            throws DangerousPatternException {
        
        ArrayList<Item> clonedStack = (ArrayList<Item>) this.stack.clone();
        HashMap<Integer, Item> clonedPool = new HashMap<>(this.itemsPool);
        
        try {
            InstructionList handlers = this.instructionsPool;
            InstructionHandle instruction = handlers
                    .findHandle(startInstruction);
            
            while (instruction != null) {
                switch (this.watchOpcode(instruction)) {
                    case 0:
                        return false;
                    case 1:
                        break;
                }
                instruction = instruction.getNext();
            }
            
        } finally {
            this.stack = clonedStack;
            this.itemsPool = clonedPool;
        }
        
        return false;
    }

    /**
     * Runs through specified class and find index of method specified by
     * methodName. The index is than used as key to obtain handlers. Note: Not
     * the best solution
     *
     * @param fullyQualifiedClassName
     * @param methodName
     * @return
     */
    private InstructionList provideHandlersToMethod(
            String fullyQualifiedClassName, String methodName) {
        
        InstructionList handlers = null;
        try {
            JavaClass cl = Repository.lookupClass(fullyQualifiedClassName);
            ClassGen cg = new ClassGen(cl);
            Method[] methods = cg.getMethods();
            int methodIndex = 0;
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().equals(methodName)) {
                    methodIndex = i;
                }
            }
            MethodGen mg = new MethodGen(
                    cg.getMethodAt(methodIndex), null, cg.getConstantPool());
            handlers = mg.getInstructionList();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BugPatternDetectorImpl.class.getName())
                    .log(
                    Level.SEVERE, "The method " + methodName
                    + " was not found in "
                    + fullyQualifiedClassName + " class.");
        }
        return handlers;
    }

    /**
     * The class is called to determine behavior of program based on instruction
     * at current position. The function is part of indirect recursion with
     * method runInstructions which is called when the conditional split is
     * required.
     *
     * The function covers basic operands for pushing integers and (string)
     * objects stack as long as retrieving them back. Also it covers conditions
     * for not null and null operations which results into separate execution of
     * both branches conditioned on item on stack.
     *
     * @param instruction "on the stack"
     * @return 0 - no dangerous, end; 1 - continue in check
     * @throws DangerousPatternException when dangerous pattern found
     */
    private int watchOpcode(InstructionHandle instruction)
            throws DangerousPatternException {
        
        short opcode = instruction.getInstruction().getOpcode();
        ItemInt tempItemInt;
        Item temp;
        switch (opcode) {
            case ICONST_0:
                tempItemInt = new ItemInt(false);
                tempItemInt.setValue(0);
                this.push(tempItemInt);
                break;
            case ICONST_1:
                tempItemInt = new ItemInt(false);
                tempItemInt.setValue(1);
                this.push(tempItemInt);
                break;
            case ICONST_2:
                tempItemInt = new ItemInt(false);
                tempItemInt.setValue(2);
                this.push(tempItemInt);
                break;
            case ICONST_M1:
                tempItemInt = new ItemInt(false);
                tempItemInt.setValue(-1);
                this.push(tempItemInt);
                break;
            case RETURN:
                return 0;
            case ARETURN:
            case IRETURN:
                this.pop();
                return 0;
            case ACONST_NULL:
                temp = new Item(true);
                this.push(temp);
                break;
            case ALOAD_1:
                this.push(this.takeFromItemPool(1));
                break;
            case ALOAD_2:
                this.push(this.takeFromItemPool(2));
                break;
            case ALOAD_3:
                this.push(this.takeFromItemPool(3));
                break;
            case ALOAD:
                this.push(this.takeFromItemPool(this.parseValue(instruction, " ")));
                break;
            case AALOAD:
                //TODO: Rework. Outer variables would be nicer!
                tempItemInt = (ItemInt) this.pop();
                temp = this.pop();
                ItemArray list;
                
                if (temp instanceof ItemArray) {
                    list = (ItemArray) temp;
                    this.push(list.getItem(tempItemInt.getValue()));
                } else {
                    list = new ItemArray();
                    list.setAddress(temp.getAddress());
                    this.putToItemPool(list);
                    temp.setAddress(-1);
                    this.push(temp);
                }

                break;
            case AASTORE:
                Item value = this.pop();
                tempItemInt = (ItemInt) this.pop();
                temp = this.pop();
                ItemArray list2;
                
                if (temp instanceof ItemArray) {
                    list2 = (ItemArray) temp;
                    list2.putItem(tempItemInt.getValue(), value);
                } else {
                    list2 = new ItemArray();
                    list2.setAddress(temp.getAddress());
                    list2.putItem(tempItemInt.getValue(), value);
                    this.putToItemPool(list2);
                }
                break;
            case LDC:
                temp = new Item(false);
                this.push(temp);
                break;
            case ASTORE_1:
                temp = this.pop();
                temp.setAddress(1);
                this.putToItemPool(temp);
                break;
            case ASTORE_2:
                temp = this.pop();
                temp.setAddress(2);
                this.putToItemPool(temp);
                break;
            case ASTORE_3:
                temp = this.pop();
                temp.setAddress(3);
                this.putToItemPool(temp);
                break;
            case ASTORE:
                temp = this.pop();
                temp.setAddress(this.parseValue(instruction, " "));
                this.putToItemPool(temp);
                break;
            case INVOKEVIRTUAL:
                if (this.lookAtStack().isDangerous()) {
                    throw new DangerousPatternException(
                            "Possibility of invoking method on null object");
                }
                break;
            case IFNONNULL:
                temp = this.pop();
                //TODO: make local variable fot that item, this is ugly
                if (temp.isDangerous()) {
                    temp.setDangerous(false);
                    this.putToItemPool(temp);
                    this.runInstructions(this.parseValue(instruction, " -> "));
                    temp.setDangerous(true);
                } else {
                    this.putToItemPool(temp);
                    this.runInstructions(this.parseValue(instruction, " -> "));
                    return 0;
                }
                break;
            case IFNULL:
                temp = this.pop();
                if (temp.isDangerous()) {
                    this.putToItemPool(temp);
                    this.runInstructions(this.parseValue(instruction, " -> "));
                    return 0;
                } else {
                    this.putToItemPool(temp);
                }
                break;
            case POP:
                this.pop();
                break;
            default:
                Logger.getLogger(BugPatternDetectorImpl.class.getName())
                        .log(
                        Level.WARNING, "The unknown opcode called: "
                        + instruction.getInstruction().getName());
                break;
        }
        return 1;
    }

    /**
     * Pushes item on stack
     *
     * @param item
     */
    private void push(Item item) {
        this.stack.add(item);
    }

    /**
     * Retrieves item from stack
     *
     * @return
     */
    private Item pop() {
        return this.stack.remove(this.stack.size() - 1);
    }

    /**
     * Retrieve item from stack without pop it
     *
     * @return
     */
    private Item lookAtStack() {
        return this.stack.get(this.stack.size() - 1);
    }

    /**
     * Some values are expected to parse (e.g. ifnonnul -> 7). This method
     * returns second argument.
     *
     * Note: Taking it from toString() is not best but I haven't found
     * workaround yet
     *
     * @param instruction
     * @param delimiter
     * @return
     */
    private int parseValue(InstructionHandle instruction, String delimiter) {
        String instString = instruction.getInstruction().toString(false);
        return Integer.parseInt(instString.split(delimiter)[1]);
    }

    /**
     * Takes value from pool of used objects.
     *
     * @param index
     * @return
     */
    private Item takeFromItemPool(int index) {
        Item item;
        
        if (this.itemsPool.containsKey(Integer.valueOf(index))) {
            item = this.itemsPool.get(index);
        } else {
            item = new Item(true);
            item.setAddress(index);
            this.itemsPool.put(index, item);
        }
        return item;
    }

    /**
     * Inserting item into pool of items, replacing the old one.
     *
     * @param item
     */
    private void putToItemPool(Item item) {
        this.itemsPool.put(item.getAddress(), item);
    }

    /**
     * Internal debug method which writes down state of program.
     *
     * Note: I left it here for future testing.
     *
     * @param instr
     */
    private void debug(String instr) {
        System.out.println("");
        System.out.println("DEBUG:");
        System.out.println("Instruction: " + instr);
        System.out.println("Stack: " + this.stack.toString());
        System.out.println("Pool: " + this.itemsPool.toString());
        System.out.println("--------------------------------------");
    }
}
