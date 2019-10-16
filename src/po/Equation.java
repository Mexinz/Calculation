package po;

import java.util.List;
import java.util.Objects;

/**
 * @Author: Mexinz
 * @Date: 2019/10/15
 */
public class Equation {
    private String equation;
    private List<Process> processes;

    public Equation(String equation, List<Process> processes) {
        this.equation = equation;
        this.processes = processes;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equation equation1 = (Equation) o;
        return equation1.processes.equals(processes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(equation, processes);
    }
}
