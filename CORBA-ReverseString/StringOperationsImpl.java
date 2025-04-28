import ReverseModule.StringOperationsPOA;

public class StringOperationsImpl extends StringOperationsPOA {

    @Override
    public String reverse(String str) {
        String reverseStr = "";

        for(int i = str.length() - 1; i >= 0; i--) {
            reverseStr += str.charAt(i);
        }

        return reverseStr;
    }

}
