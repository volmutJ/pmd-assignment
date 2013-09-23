package cz.muni.fi.nullpointerassignment;

class DangerousPatternException extends Exception
{
      //Parameterless Constructor
      public DangerousPatternException() {}

      //Constructor that accepts a message
      public DangerousPatternException(String message)
      {
         super(message);
      }
 }