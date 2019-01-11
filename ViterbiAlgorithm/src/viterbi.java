import java.util.Scanner;

public class viterbi {
	public static  void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		String[] states = {"HOT","COLD"};
		int[] ob = {1, 2, 3};
		double[] initialProbs = {0.8, 0.2};
		String[][] stateTransitions = {{"0", "HOT", "COLD"}, {"HOT", "0.7", "0.3"}, {"COLD", "0.4", "0.6"}};
		String[][] observationTransitions = {{"0", "1", "2", "3"}, {"HOT", "0.2", "0.4", "0.4"}, {"COLD", "0.5", "0.4", "0.1"}};
		System.out.println("Enter the test observation sequence:");
		String givenObservationSeq = sc.next();
		String[][] probMatrix = new String[states.length + 1][givenObservationSeq.length() + 1];
        String[][] backMatrix = new String[states.length + 1][givenObservationSeq.length() + 1];
        probMatrix[0][0] = " ";
        backMatrix[0][0] = " ";
        for (int len = 0; len < states.length; len++) 
        {
            probMatrix[len + 1][0] = states[len];
            backMatrix[len + 1][0] = states[len];
        }
        for (int len = 0; len < givenObservationSeq.length(); len++) 
        {
            probMatrix[0][len + 1] = givenObservationSeq.charAt(len) + "";
            backMatrix[0][len + 1] = givenObservationSeq.charAt(len) + "";
        }
        for (int col = 1; col < givenObservationSeq.length() + 1; col++) {
            for (int row = 1; row < states.length + 1; row++) {
                if (col == 1) {
                    String lS = probMatrix[row][0];
                    String uS = probMatrix[0][col];
                    g:
                    {
                        for (int r = 0; r < states.length + 1; r++) {
                            for (int c = 0; c < ob.length + 1; c++) {
                                if (observationTransitions[r][0].equals(lS) && observationTransitions[0][c].equals(uS)) {
                                    if (probMatrix[row][0].equals("HOT")) {
                                        probMatrix[row][col] = initialProbs[0] * Double.parseDouble(observationTransitions[r][c]) + "";
                                        backMatrix[row][col] = "LH";
                                        break g;

                                    } else {
                                        if (probMatrix[row][0].equals("COLD")) {
                                            probMatrix[row][col] = initialProbs[1] * Double.parseDouble(observationTransitions[r][c]) + "";
                                            backMatrix[row][col] = "LC";
                                            break g;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    String lS= probMatrix[row][0];
                    String uS = probMatrix[0][col];
                    for (int r = 0; r < states.length + 1; r++) {
                        for (int c = 0; c < ob.length + 1; c++) {
                            if (observationTransitions[r][0].equals(lS) && observationTransitions[0][c].equals(uS)) {
                                if (row == 1) {

                                    probMatrix[row][col] = Double.parseDouble(observationTransitions[r][c]) * Math.max(Double.parseDouble(probMatrix[row][col - 1]) * Double.parseDouble(stateTransitions[1][1]), Double.parseDouble(probMatrix[row + 1][col - 1]) * Double.parseDouble(stateTransitions[2][1])) + "";
                                    double firstValue = Double.parseDouble(probMatrix[row][col - 1]) * Double.parseDouble(stateTransitions[1][1]);
                                    double secondValue = Double.parseDouble(probMatrix[row + 1][col - 1]) * Double.parseDouble(stateTransitions[2][1]);
                                    if (firstValue > secondValue) {
                                        backMatrix[row][col] = "LH";
                                    } else {
                                        backMatrix[row][col] = "BD";
                                    }
                                } else {
                                    if (row == 2) {

                                        probMatrix[row][col] = Double.parseDouble(observationTransitions[r][c]) * Math.max(Double.parseDouble(probMatrix[row - 1][col - 1]) * Double.parseDouble(stateTransitions[1][2]), Double.parseDouble(probMatrix[row][col - 1]) * Double.parseDouble(stateTransitions[2][2])) + "";
                                        double firstValue = Double.parseDouble(probMatrix[row - 1][col - 1]) * Double.parseDouble(stateTransitions[1][2]);
                                        double secondValue = Double.parseDouble(probMatrix[row][col - 1]) * Double.parseDouble(stateTransitions[2][2]);
                                        if (firstValue > secondValue) {
                                            backMatrix[row][col] = "UD";
                                        } else {
                                            backMatrix[row][col] = "LC";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int row = 0; row < states.length + 1; row++) {
            for (int col = 0; col < givenObservationSeq.length() + 1; col++) {

                System.out.print(probMatrix[row][col] + "\t\t");

            }
            System.out.println();
        }
        System.out.println();
        String str = "";
        String Answer = "";
        double value = Math.max(Double.parseDouble(probMatrix[1][givenObservationSeq.length()]), Double.parseDouble(probMatrix[2][givenObservationSeq.length()]));
        if (Double.parseDouble(probMatrix[1][givenObservationSeq.length()]) > Double.parseDouble(probMatrix[2][givenObservationSeq.length()])) {
            str = backMatrix[1][givenObservationSeq.length()];
        } else {
            str = backMatrix[2][givenObservationSeq.length()];
        }
        int i = givenObservationSeq.length();

        while (i > 0) {
            if (str.equals("LH")) {
                str = backMatrix[1][i - 1];
                Answer += "HOT ";

            } else if (str.equals("UD")) {
                str = backMatrix[1][i - 1];
                Answer += "COLD ";
            } else if (str.equals("BD")) {
                str = backMatrix[2][i - 1];
                Answer += "HOT ";
            } else if (str.equals("LC")) {
                str = backMatrix[2][i - 1];
                Answer += "COLD ";
            }

            i--;
        }
        String ans = "";
        String[] AnswerMatrix = Answer.split(" ");

        for (int len = AnswerMatrix.length - 1; len >= 0; len--) {
            ans += AnswerMatrix[len] + " ";
        }
        System.out.println("Final Probability: " + Math.max(Double.parseDouble(probMatrix[1][givenObservationSeq.length()]), Double.parseDouble(probMatrix[2][givenObservationSeq.length()])));
        System.out.println("Hidden States: " + ans);
    }

}
	


	