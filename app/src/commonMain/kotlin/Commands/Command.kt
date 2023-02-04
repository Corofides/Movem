package Commands

interface Command {

    fun exec();
    fun undo();

}
