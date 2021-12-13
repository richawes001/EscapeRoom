import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
Searching Door -> Searching Door Frame / Searching Door Knob
Searching Door Frame -> Notices it's Sturdy, Prompts the use of an Axe    (Go Back, Use Axe)
Searching Door Knob -> Notices it's Locked, Prompts the use of a Key      (Go Back, Use Door Key)

Using Axe -> You are Free
Using Key -> You are Free

Searching Dresser -> Searching Decoration / Searching Cabinets
Searching Decoration -> Notices a Key, Takes Cabinet Key                  (Take Cabinet Key, Go Back)
Searching Cabinets -> Notices it's Locked, Prompts the use of a Key       (Use Cabinet Key, Go Back)

Taking Cabinet Key -> Puts in Inventory for Later
Using Cabinet Key -> Opens Cabinet, Notices a Key, Takes Door Key         (Take Door Key, Go Back)

Searching Bed -> Searching Pillows / Searching Mattress
Searching Pillows -> Finds Hole in Mattress, Remembers This               (Remember Hole, Go Back)
Searching Mattress -> Finds nothing interesting, but Wonders for More     (Use Knowledge, Go Back)

Remembering Hole -> Puts in "Inventory" for Later
Using Knowledge -> Using the Knowledge of the Hole, you Reach into the Mattress, and Find an Axe   (Take Axe, Go Back)
 */

public class EscapeRoom {

    boolean holeKnowledge = false;
    boolean axeObtained = false;
    boolean cabinetKeyObtained = false;
    boolean doorKeyObtained = false;
    boolean doorOpen = false;
    boolean doorBroken = false;

    JPanel panel;
    JLabel time, mainInfo, bedInfo, dresserInfo, doorInfo, pillowsInfo, mattressInfo, decorationInfo, cabinetInfo, knobInfo, frameInfo;
    JButton startGame, goToBed, goToDresser, goToDoor, goBack, searchPillows, searchMattress, searchCabinets, searchDecoration, searchKnob, searchFrame, rememberHole, useHoleKnowledge, takeCabinetKey, useCabinetKey, useDoorKey, useAxe, secret;
    int maxSeconds = 60;
    int seconds = maxSeconds;
    Timer timer;

    public static void main(String[] args) {
        new EscapeRoom();
    }

    public EscapeRoom(){

        JFrame frame = new JFrame("Escape Room");
        frame.setSize(800, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        time = new JLabel();

        //main screen info
        mainInfo = new JLabel();

        //set of options info
        bedInfo = new JLabel();
        dresserInfo = new JLabel();
        doorInfo = new JLabel();

        //set of options 2 info
        pillowsInfo = new JLabel();
        mattressInfo = new JLabel();
        decorationInfo = new JLabel();
        cabinetInfo = new JLabel();
        knobInfo = new JLabel();
        frameInfo = new JLabel();

        //main screen buttons
        startGame = new JButton("Click to Start");
        goToBed = new JButton("Investigate Bed");
        goToDresser = new JButton("Investigate Dresser");
        goToDoor = new JButton("Investigate Door");
        goBack = new JButton("Go Back");

        //set of options buttons
        searchPillows = new JButton("Search Pillows");
        searchMattress = new JButton("Search Mattress");
        searchCabinets = new JButton("Search Cabinets");
        searchDecoration = new JButton("Search Decoration");
        searchKnob = new JButton("Search Door Knob");
        searchFrame = new JButton("Search Door Frame");

        //set of options buttons 2
        rememberHole = new JButton("Remember the Hole");
        useHoleKnowledge = new JButton("Use Hole Knowledge");
        takeCabinetKey = new JButton("Take Cabinet Key");
        useCabinetKey = new JButton("Use Cabinet Key");
        useDoorKey = new JButton("Use Door Key");
        useAxe = new JButton("Use Axe");

        secret = new JButton("Do something cool.");

        //main screen listeners
        goToBed.addActionListener(new BedListener());
        goToDresser.addActionListener(new DresserListener());
        goToDoor.addActionListener(new DoorListener());
        goBack.addActionListener(new GoBackListener());

        //set of options listeners
        searchPillows.addActionListener(new PillowListener());
        searchMattress.addActionListener(new MattressListener());
        searchDecoration.addActionListener(new DecorationListener());
        searchCabinets.addActionListener(new CabinetListener());
        searchKnob.addActionListener(new KnobListener());
        searchFrame.addActionListener(new FrameListener());

        //set of options listeners 2
        rememberHole.addActionListener(new HoleListener());
        useHoleKnowledge.addActionListener(new HoleKnowledgeListener());
        takeCabinetKey.addActionListener(new CabinetKeyListener());
        useCabinetKey.addActionListener(new UseCabinetKeyListener());
        useDoorKey.addActionListener(new DoorKeyListener());
        useAxe.addActionListener(new AxeListener());

        secret.addActionListener(new secretListener());

        timer = new Timer(1000, new SecondsListener());
        panel = new JPanel();

        startGame.addActionListener(new StartListener());

        panel.add(startGame);

        frame.add(panel);
        frame.setVisible(true);

    }

    //clicking start button
    private class StartListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            panel.remove(startGame);
            mainInfo.setText("You're trapped in a random bedroom. How will you get out? Take a look around, and maybe you'll figure it out.");
            panel.add(mainInfo);
            panel.add(time);
            panel.add(goToDoor);
            panel.add(goToDresser);
            panel.add(goToBed);
            panel.updateUI();
            timer.start();

        }

    }

    //timer countdown
    private class SecondsListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            seconds--;
            time.setText(Integer.toString(seconds));

            if(seconds <= 0){
                timer.stop();
                time.setText("You didn't escape within time. That sure is unfortunate.");
                //removing set of options 2
                panel.remove(rememberHole);
                panel.remove(useHoleKnowledge);
                panel.remove(takeCabinetKey);
                panel.remove(useCabinetKey);
                panel.remove(useDoorKey);
                panel.remove(useAxe);
                //removing set of options info
                panel.remove(frameInfo);
                panel.remove(knobInfo);
                panel.remove(cabinetInfo);
                panel.remove(decorationInfo);
                panel.remove(mattressInfo);
                panel.remove(pillowsInfo);
                //removing set of options
                panel.remove(searchFrame);
                panel.remove(searchKnob);
                panel.remove(searchDecoration);
                panel.remove(searchCabinets);
                panel.remove(searchMattress);
                panel.remove(searchPillows);
                //removing stuff
                panel.remove(mainInfo);
                panel.remove(goBack);
                //remove main options
                panel.remove(goToBed);
                panel.remove(goToDresser);
                panel.remove(goToDoor);
                panel.updateUI();
            }

        }

    }

    //searching the bed
    private class BedListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            panel.remove(mainInfo);
            panel.remove(goToDoor);
            panel.remove(goToDresser);
            panel.remove(goToBed);
            bedInfo.setText("This is a bed. You can either check the pillows, or you can check the mattress.");
            panel.add(bedInfo);
            panel.add(goBack);
            panel.add(searchMattress);
            panel.add(searchPillows);
            panel.updateUI();

            if(holeKnowledge){
                bedInfo.setText("You should totally check out that mattress.");
            }

            if(holeKnowledge && axeObtained){
                bedInfo.setText("Why are you here? You have everything from the bed.");
            }

        }

    }

    //searching the pillows
    private class PillowListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            pillowsInfo.setText("You search the pillows, and find a hole in the mattress!");
            panel.add(pillowsInfo);
            panel.remove(bedInfo);
            panel.remove(searchMattress);
            panel.remove(searchPillows);
            panel.add(rememberHole);
            panel.add(goBack);
            panel.updateUI();

            if(holeKnowledge){
                pillowsInfo.setText("You already have hole knowledge.");
                panel.remove(rememberHole);
            }

        }

    }

    //searching the mattress
    private class MattressListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            mattressInfo.setText("You find nothing interesting, maybe check under the pillows?");
            panel.add(mattressInfo);
            panel.remove(bedInfo);
            panel.remove(searchMattress);
            panel.remove(searchPillows);
            panel.add(goBack);
            panel.updateUI();

            if(holeKnowledge){
                mattressInfo.setText("Ready to use that hole knowledge?");
                panel.add(useHoleKnowledge);
            }

            if(axeObtained){
                mattressInfo.setText("You already have the axe from the mattress.");
                panel.remove(useHoleKnowledge);
            }

        }

    }

    //remembering the hole
    private class HoleListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            panel.remove(rememberHole);
            panel.updateUI();
            holeKnowledge = true;
            pillowsInfo.setText("You now have hole knowledge!");

        }

    }

    //using hole knowledge
    private class HoleKnowledgeListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            if(holeKnowledge){
                panel.remove(useHoleKnowledge);
                panel.updateUI();
                axeObtained = true;
                mattressInfo.setText("You now have an axe!");
            }

        }

    }

    //searching the dresser
    private class DresserListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            panel.remove(mainInfo);
            panel.remove(goToDoor);
            panel.remove(goToDresser);
            panel.remove(goToBed);
            dresserInfo.setText("You look at the dresser. You can either check the decoration on top, or search the cabinets.");
            panel.add(dresserInfo);
            panel.add(goBack);
            panel.add(searchDecoration);
            panel.add(searchCabinets);
            panel.updateUI();

            if(cabinetKeyObtained){
                dresserInfo.setText("You should totally check out those cabinets.");
            }

            if(cabinetKeyObtained && doorKeyObtained){
                dresserInfo.setText("Why are you here? You already have everything from the dresser.");
            }

        }

    }

    //searching the decoration
    private class DecorationListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            decorationInfo.setText("You search the decoration on top of the dresser, you find a small key!");
            panel.add(decorationInfo);
            panel.remove(dresserInfo);
            panel.remove(searchCabinets);
            panel.remove(searchDecoration);
            panel.add(takeCabinetKey);
            panel.add(goBack);
            panel.updateUI();

            if(cabinetKeyObtained){
                decorationInfo.setText("You already have the cabinet key. Nothing to see here.");
                panel.remove(takeCabinetKey);
            }

        }

    }

    //searching the cabinets
    private class CabinetListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            cabinetInfo.setText("You check the cabinets, they're locked, maybe find a key?");
            panel.add(cabinetInfo);
            panel.remove(dresserInfo);
            panel.remove(searchCabinets);
            panel.remove(searchDecoration);
            panel.add(goBack);
            panel.updateUI();

            if(cabinetKeyObtained){
                cabinetInfo.setText("Seems like that cabinet key will come in handy!");
                panel.add(useCabinetKey);
            }

            if(doorKeyObtained){
                cabinetInfo.setText("Nothing left in the cabinets.");
                panel.remove(useCabinetKey);
            }

        }

    }

    //taking the cabinet key
    private class CabinetKeyListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            panel.remove(takeCabinetKey);
            panel.updateUI();
            cabinetKeyObtained = true;
            decorationInfo.setText("You now have the cabinet key!");

        }

    }

    //using the cabinet key
    private class UseCabinetKeyListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            if(cabinetKeyObtained){
                panel.remove(useCabinetKey);
                panel.updateUI();
                doorKeyObtained = true;
                cabinetInfo.setText("You now have the door key!");
            }

        }

    }

    //searching the door
    private class DoorListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            panel.remove(mainInfo);
            panel.remove(goToDoor);
            panel.remove(goToDresser);
            panel.remove(goToBed);
            doorInfo.setText("You look at the door. It's closed, check the door knob or frame perhaps?");
            panel.add(doorInfo);
            panel.add(goBack);
            panel.add(searchFrame);
            panel.add(searchKnob);
            panel.updateUI();

            if(doorKeyObtained || axeObtained){
                doorInfo.setText("Seems like you have a method of escaping.");
            }

            if(doorKeyObtained && axeObtained){
                doorInfo.setText("Wanna do something cool, and totally useless?");
                panel.add(secret);
                panel.remove(searchFrame);
                panel.remove(searchKnob);
                panel.updateUI();
            }

        }

    }

    //searching the door knob
    private class KnobListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            knobInfo.setText("You check the door knob, it's locked, maybe find a key?");
            panel.add(knobInfo);
            panel.remove(doorInfo);
            panel.remove(searchFrame);
            panel.remove(searchKnob);
            panel.add(goBack);
            panel.updateUI();

            if(doorKeyObtained){
                knobInfo.setText("Knock knock. Who's there? Freedom. ");
                panel.add(useDoorKey);
            }

        }

    }

    //searching the door frame
    private class FrameListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            frameInfo.setText("You check the door frame, it's sturdy, maybe find an axe?");
            panel.add(frameInfo);
            panel.remove(doorInfo);
            panel.remove(searchFrame);
            panel.remove(searchKnob);
            panel.add(goBack);
            panel.updateUI();

            if(axeObtained){
                frameInfo.setText("Chippity choppity. Use this axe to get off this unknown property.");
                panel.add(useAxe);
            }

        }

    }

    //using door key
    private class DoorKeyListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            if(doorKeyObtained){
                doorOpen = true;
                knobInfo.setText("You opened the door! Freedom!");
                //removing set of options 2
                panel.remove(rememberHole);
                panel.remove(useHoleKnowledge);
                panel.remove(takeCabinetKey);
                panel.remove(useCabinetKey);
                panel.remove(useDoorKey);
                panel.remove(useAxe);
                //removing set of options info
                panel.remove(frameInfo);
                panel.remove(cabinetInfo);
                panel.remove(decorationInfo);
                panel.remove(mattressInfo);
                panel.remove(pillowsInfo);
                //removing set of options
                panel.remove(searchFrame);
                panel.remove(searchKnob);
                panel.remove(searchDecoration);
                panel.remove(searchCabinets);
                panel.remove(searchMattress);
                panel.remove(searchPillows);
                //removing stuff
                panel.remove(goBack);
                panel.remove(startGame);
                panel.updateUI();
                timer.stop();
            }

        }

    }

    //using axe
    private class AxeListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            if(axeObtained){
                doorBroken = true;
                frameInfo.setText("You broke down the door! Freedom!");
                //removing set of options 2
                panel.remove(rememberHole);
                panel.remove(useHoleKnowledge);
                panel.remove(takeCabinetKey);
                panel.remove(useCabinetKey);
                panel.remove(useDoorKey);
                panel.remove(useAxe);
                //removing set of options info
                panel.remove(knobInfo);
                panel.remove(cabinetInfo);
                panel.remove(decorationInfo);
                panel.remove(mattressInfo);
                panel.remove(pillowsInfo);
                //removing set of options
                panel.remove(searchFrame);
                panel.remove(searchKnob);
                panel.remove(searchDecoration);
                panel.remove(searchCabinets);
                panel.remove(searchMattress);
                panel.remove(searchPillows);
                //removing stuff
                panel.remove(goBack);
                panel.remove(startGame);
                panel.updateUI();
                timer.stop();
            }

        }

    }

    //having all objects
    private class secretListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            doorInfo.setText("You unlock the door with the key, and then with the axe, you chop down the already unlocked door. You feel super cool. Freedom!");
            panel.remove(secret);
            panel.remove(goBack);
            panel.updateUI();
            timer.stop();

            }

        }

    //going back
    private class GoBackListener implements ActionListener{

        public void actionPerformed(ActionEvent actionEvent){

            //removing set of options 2
            panel.remove(rememberHole);
            panel.remove(useHoleKnowledge);
            panel.remove(takeCabinetKey);
            panel.remove(useCabinetKey);
            panel.remove(useDoorKey);
            panel.remove(useAxe);
            //removing set of options info
            panel.remove(frameInfo);
            panel.remove(knobInfo);
            panel.remove(cabinetInfo);
            panel.remove(decorationInfo);
            panel.remove(mattressInfo);
            panel.remove(pillowsInfo);
            //removing set of options
            panel.remove(searchFrame);
            panel.remove(searchKnob);
            panel.remove(searchDecoration);
            panel.remove(searchCabinets);
            panel.remove(searchMattress);
            panel.remove(searchPillows);
            //removing stuff
            panel.remove(goBack);
            panel.remove(startGame);
            panel.remove(bedInfo);
            panel.remove(dresserInfo);
            panel.remove(doorInfo);
            //re-adding main stuff
            mainInfo.setText("You're trapped in a random bedroom. How will you get out? Take a look around, and maybe you'll figure it out.");
            panel.add(mainInfo);
            panel.add(goToDoor);
            panel.add(goToDresser);
            panel.add(goToBed);
            panel.updateUI();

            if(axeObtained && doorKeyObtained){
                mainInfo.setText("Why did you collect everything? You only need one method of escape. Whatever... get outta' here.");
                panel.remove(goToBed);
                panel.remove(goToDresser);
                panel.updateUI();
            }

        }

    }

}
