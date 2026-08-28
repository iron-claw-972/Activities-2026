
package frc.robot.util.ShuffleBoard.Tabs;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.subsystems.thing;
import frc.robot.util.ShuffleBoard.ShuffleBoardTabs;

public class SubsystemTab extends ShuffleBoardTabs {
    // TODO 2.3.11: Create variable for subsystem
    thing myThing;

    public SubsystemTab(thing myThing) {
        this.myThing = myThing;

    }

    public void createEntries() {
        tab = Shuffleboard.getTab("Subsystem");

        // TODO 2.4.7: Add Mechanism2d
        tab.add("mech", myThing.myMech2D);
        // TODO 3.3.13: Add command buttons

        // TODO 5.3.1: Add PID

    }

    public void update() {
    }
}
