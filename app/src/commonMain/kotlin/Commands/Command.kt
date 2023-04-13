package Commands

/**
 * Command
 */
interface Command {
    fun exec(): Boolean
    fun undo()
}
