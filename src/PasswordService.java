package src;

import java.util.Random;
public class PasswordService {

    public static void main(String[] args)throws Exception {

        Password p1 = new Password("abcdef4");
        p1.setPassword("abcdef456432");
        System.out.println(p1.toString());
    }
}
class InvalidPassword extends Exception {
    public InvalidPassword(String errorMessage) {
        super(errorMessage);
    }
}

class Password{
    private String oldPassword;
    private String newPassword;

    public String toString(){
        StringBuilder str = new StringBuilder(""+oldPassword.charAt(0));
        Random rand = new Random();
        int asterisk = rand.nextInt(19)+1;
        for(int i=0;i<asterisk;i++){
            str.append("*");
        }
        str.append(oldPassword.charAt(oldPassword.length()-1));
        return str.toString();
    }

    public Password(){
        this.oldPassword = " ";
    }
    public Password(String password){
        this.oldPassword = password;
    }
    private boolean hasDigit(int passwordLength){
        if(passwordLength==0){
            return false;
        }else{
            if(Character.isDigit(this.newPassword.charAt(passwordLength-1))){
                return true;
            }else{
                return hasDigit(passwordLength-1);
            }
        }
    }
    private boolean equals(int passwordsLength){
        if(passwordsLength==0){
            return true;
        }else{
            if(this.oldPassword.charAt(passwordsLength-1) == this.newPassword.charAt(passwordsLength-1))
            {
                return equals(passwordsLength-1);
            }else{
                return false;
            }
        }
    }
    private boolean isDifferentEnough(int requiredDifference, int passwordsLength){
        if(passwordsLength==0){
            return false;
        }else{
            if(requiredDifference==0){
                return true;
            }else{
                if(this.oldPassword.charAt(passwordsLength-1)!=this.newPassword.charAt(passwordsLength-1)){
                    return isDifferentEnough(requiredDifference-1,passwordsLength-1);
                }else{
                    return isDifferentEnough(requiredDifference,passwordsLength-1);
                }
            }
        }
    }
    private boolean isDifferentEnough(int requiredDifference){
        String shorter = this.oldPassword.length()<= this.newPassword.length() ? oldPassword : newPassword;
        for(int i=0;i<shorter.length();i++){
            if(this.oldPassword.charAt(i)!=this.newPassword.charAt(i)){
                requiredDifference=requiredDifference-1;
            }
        }
        requiredDifference = requiredDifference - Math.abs(this.newPassword.length() - this.oldPassword.length());
        if(requiredDifference<=0){
            return true;
        }else{
            return false;
        }
    }
    public void setPassword(String newPassword)throws Exception{
        this.newPassword = newPassword;
        int requiredDifference = 4;
        if(hasDigit(this.newPassword.length())){
            if(this.oldPassword.length()==this.newPassword.length()){
                if(equals(this.newPassword.length())){
                    throw new InvalidPassword("new password can not be same as old password.");
                }else{
                    if(isDifferentEnough(requiredDifference,this.newPassword.length())){
                        this.oldPassword = this.newPassword;
                        System.out.println("password has been changed successfully, ");
                    }else throw new InvalidPassword("new password can not be similar to old password. At least position of "+requiredDifference+" digits must be different.");
                }
            }else{
                if(isDifferentEnough(requiredDifference)){
                    this.oldPassword = this.newPassword;
                    System.out.println("password has been changed successfully.");
                }else throw new InvalidPassword("new password can not be similar to old password. At least position of "+requiredDifference+" digits must be different.");
            }
        }else{
            throw new InvalidPassword("password must contain at least 1 digit");
        }
    }
}