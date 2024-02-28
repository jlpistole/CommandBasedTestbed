// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.InterruptTestSubsystem;
import frc.robot.subsystems.ShooterSequenceTestSubsystem;
import frc.robot.subsystems.SingleMotorSubsystem;
import frc.robot.subsystems.StartEndTestSubsystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final SingleMotorSubsystem m_singleMotorSubsystem = new SingleMotorSubsystem();
  private final InterruptTestSubsystem m_interruptTestSubsystem = new InterruptTestSubsystem();
  private final StartEndTestSubsystem startEndTestSubsystem = new StartEndTestSubsystem(); 
  private final ShooterSequenceTestSubsystem shooterSequenceTestSubsystem = new ShooterSequenceTestSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
 // private final CommandXboxController m_driverController =
   //   new CommandXboxController(OperatorConstants.kDriverControllerPort);

  //private final CommandPS4Controller m_driverController =
    //  new CommandPS4Controller(OperatorConstants.kDriverControllerPort);
    private final GenericGamepad controller = new GenericGamepad(new CommandPS4Controller(OperatorConstants.kDriverControllerPort));

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    /* 
    m_driverController.R2().whileTrue(m_singleMotorSubsystem.spinIn());
    //m_driverController.L2().whileTrue(m_singleMotorSubsystem.spinOut());
    m_driverController.L2().whileTrue(m_singleMotorSubsystem.spinOut());
    m_driverController.triangle()
      .onTrue(m_interruptTestSubsystem.runLongCommand()
      .until(m_driverController.cross())
      .withInterruptBehavior(InterruptionBehavior.kCancelIncoming));
    m_driverController.square().onTrue(m_interruptTestSubsystem.runShortCommand());
    */
    controller.triangle_y.onTrue(startEndTestSubsystem.runTriCommand().withInterruptBehavior(InterruptionBehavior.kCancelIncoming));
    controller.square_x.onTrue(startEndTestSubsystem.runSquareCommand().withInterruptBehavior(InterruptionBehavior.kCancelIncoming));

    controller.rightTriggerB.onTrue(shooterSequenceTestSubsystem.runShooterSequence().until(() -> { return !controller.rightTriggerB.getAsBoolean(); }));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
