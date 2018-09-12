package hr.fer.zemris.java.hw06.shell;

/**
 * Each {@link ShellCommand} return shell status. If return shell status is CONTINUE program should 
 * contine with his work and if the shell status is TERMINATE, program shold be terminated.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public enum ShellStatus {
	
	/**
	 * If this status is returned program should continue with his work.
	 */
	CONTINUE, 
	
	/**
	 * If this status is returned program should be terminated.
	 */
	TERMINATE;

}
