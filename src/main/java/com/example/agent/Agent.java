package com.example.agent;

import com.example.tool.Tool;
import java.util.List;

public class Agent {
    private final String role;
    private final String goal;
    private final String backstory;
    private final List<Tool> tools;
    
    public Agent(String role, String goal, String backstory, List<Tool> tools) {
        this.role = role;
        this.goal = goal;
        this.backstory = backstory;
        this.tools = tools;
    }
    
    public String getRole() { return role; }
    public String getGoal() { return goal; }
    public String getBackstory() { return backstory; }
    public List<Tool> getTools() { return tools; }
}