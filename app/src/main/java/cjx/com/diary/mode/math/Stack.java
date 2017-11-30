package cjx.com.diary.mode.math;

import java.security.InvalidParameterException;

/**
 * description:
 * author: bear .
 * Created date:  2017/11/24.
 */
public class Stack {
    //栈的数组
    private  int[] array;
    //栈的大小
    private int mSize;
    //栈顶位置
    private int top;

    public int getmSize() {
        return mSize;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public Stack(int size) {
        this.mSize = size;
        if (size == 0) throw new InvalidParameterException("size must big than zero! s");
        this.array = new int[mSize];
        this.top=-1;
    }

    private boolean isFull() {
        return top+1 == mSize;
    }

    private boolean isEmpty() {
        return top == -1;
    }

    //压栈
    public boolean push(int value) {
        if (isFull()) {
            return false;
        } else {
            top++;
            this.array[top] = value;
            return true;
        }
    }

    public int pull() throws Exception {
        if(isEmpty()){
         throw new Exception("the stack is empty");
        }else{
            return this.array[top--];
        }
    }
}
