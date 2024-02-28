package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class ShooterSequenceTestSubsystem extends SubsystemBase {
    boolean shooterAtSpeed = false;

    @Override
    public void periodic() {
        
    }

    public Command runShooterSequence() {
        shooterAtSpeed = false;
        return startShooter().andThen(waitShooter()).andThen(runIndexer()).handleInterrupt(this::stopShooter);
    }

    private Command startShooter() {
        return this.runOnce(() -> { System.out.println("starting shooter motor PID request"); System.out.flush(); })
        .alongWith(Commands.waitSeconds(5).andThen(() -> { System.out.println("shooter wait ending"); System.out.flush(); shooterAtSpeed = true; }));
    }

    private Command waitShooter() {
        return this.run(() -> {}).until(this::getShooterAtSpeed);
    }

    private Command runIndexer() {
        return this.startEnd(() -> { System.out.println("starting indexer"); System.out.flush(); }, () -> { System.out.println("Stopping indexer"); System.out.flush(); });
    }

    private boolean getShooterAtSpeed() { return shooterAtSpeed; }

    private void stopShooter() {
        System.out.println("Stopping shooter motor"); 
        System.out.flush(); 
    }
    
}
