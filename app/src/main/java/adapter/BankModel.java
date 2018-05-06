package adapter;

/**
 * Created by hp on 2/9/2017.
 */

public class BankModel {
    String bankName,bankInterest,BankImage;

   public BankModel(){
   }

    public BankModel(String bankName,String bankInterest,String BankImage){
        this.BankImage=BankImage;
        this.bankInterest=bankInterest;
        this.bankName= bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankInterest() {
        return bankInterest;
    }

    public void setBankInterest(String bankInterest) {
        this.bankInterest = bankInterest;
    }

    public String getBankImage() {
        return BankImage;
    }

    public void setBankImage(String bankImage) {
        BankImage = bankImage;
    }
}
