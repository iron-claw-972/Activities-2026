package frc.robot.subsystems;

import java.lang.module.ModuleDescriptor.Requires;

import frc.robot.Robot;

public class ArcadeDriveCommand extends command.java{

    private final Requires addRequirements;

    public ArcadeDriveCommand() {
        stoDrivetrain instance variable;
        void addRequirements() {

        }

        @Override
        public void execute(){
            Robot.driver.getForwardTranslation();
            Robot.driver.getTurn();
            robotInit();
            drive.setDefaultCommand()

        }

    }
