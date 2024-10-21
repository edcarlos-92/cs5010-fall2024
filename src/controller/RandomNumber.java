package controller;

import java.util.List;
import java.util.Random;

/**
 * Overridden implementation of Random Number generation for the computer
  controller player so make the tests predictable.
 */
public class RandomNumber extends Random {

  private static final long serialVersionUID = 1L;
  private int state = 0; // 0 - default implementation of random
  private List<Integer> rand;
  private int randIndex = 0;
  
  /**
   * Constructor which determines based on the input if numbers need to be truly random or
    predictable for the tests.
   * @param randomNum List of integers that will used as input for the random number generation.
   */
  public RandomNumber(List<Integer> randomNum) {
    if (randomNum == null) {
      throw new IllegalArgumentException("List of Random Number cannot be Null.");
    }
    if (randomNum.size() == 0) {
      this.state  = 0;
    } else {
      this.state = 1;
    }
    this.rand = randomNum;
  }
  
  @Override
  public int nextInt(int num) {
    if (this.state == 0) {
      return super.nextInt(num);
    } else {
      return this.rand.get(this.randIndex++);
    }
  }
 
}
