
package frc.robot.util.ShuffleBoard.Tabs;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.commands.SpinXRotationCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Test;
import frc.robot.util.ShuffleBoard.ShuffleBoardTabs;


public class SubsystemTab extends ShuffleBoardTabs {
    Test test;

    public SubsystemTab(Test testInput){
        test = testInput;
    }

    public void createEntries(){
        tab = Shuffleboard.getTab("Test Subsystem");
        tab.add("Mechanism 2D", test.mechanism);
        tab.add("Idk", new SpinXRotationCommand(test, 26));
        tab.add("PID CONTROLLER", test.getPid());

        // TODO 2.4.7: Add Mechanism2d

        // TODO 3.3.13: Add command buttons

        // TODO 5.3.1: Add PID

    }


    public void update(){}
}
