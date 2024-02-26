package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

public class SingleMotorSubsystem extends SubsystemBase {
    
    // Subsystem Constants
    public static final int MOTOR_ID = 13; 
    public static final int MOTOR_FOLLOW_ID = 14;

    public static final double MOTOR_SPEED = 0.20;



    enum SpinDirection {
        SpinInDir,
        SpinOutDir,
        SpinNone
    }

    private SpinDirection currentDirection = SpinDirection.SpinNone;
    private TalonFX motor;
    private TalonFX motorFollower;
    private DutyCycleOut dutyCycleOut;

    public SingleMotorSubsystem() {
        // not actually a single motor anymore lalala
        motor = new TalonFX(MOTOR_ID);

        TalonFXConfiguration config = new TalonFXConfiguration();
        config.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = .6;
        motor.getConfigurator().apply(config);


        motorFollower = new TalonFX(MOTOR_FOLLOW_ID);
        motorFollower.setControl(new Follower(MOTOR_ID, false));
        

        dutyCycleOut = new DutyCycleOut(0.0);        
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putString("Motor Spin Direction", currentDirection.toString());
        SmartDashboard.putNumber("Motor Speed", this.motor.get());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public Command spinIn() {
        return this.runEnd(() -> { this.spinMotor(SpinDirection.SpinInDir);},
            () -> { this.stopMotor();});
    }

    public Command spinInControlCommand() {
        return this.runEnd(() -> { this.spinMotorControl(SpinDirection.SpinInDir);}, () -> { this.stopMotor();});
    }

    public Command spinOut() {
         return this.runEnd(() -> { this.spinMotor(SpinDirection.SpinOutDir);},
            () -> { this.stopMotor();});
    }

    private void stopMotor() {
        this.currentDirection = SpinDirection.SpinNone;
        this.motor.stopMotor();
    }

    private void spinMotor(SpinDirection direction) {
        switch (direction) {
            case SpinNone:
                stopMotor();
                break;
            case SpinInDir:
                this.currentDirection = SpinDirection.SpinInDir;
                this.motor.set(MOTOR_SPEED);
                break;
            case SpinOutDir:
                currentDirection = SpinDirection.SpinOutDir;
                this.motor.set(-1.0*MOTOR_SPEED);
                break;
        }
    }

    private void spinMotorControl(SpinDirection direction) {
        switch (direction) {
            case SpinNone:
                stopMotor();
                break;
            case SpinInDir:
                this.currentDirection = SpinDirection.SpinInDir;
                this.motor.setControl(dutyCycleOut.withOutput(MOTOR_SPEED));
                break;
            case SpinOutDir:
                currentDirection = SpinDirection.SpinOutDir;
                this.motor.setControl(dutyCycleOut.withOutput(-1.0*MOTOR_SPEED));
                break;
        }
    }
}
