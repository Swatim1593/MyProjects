package com.hospital.model;

public enum PatientPriority {
CRITICAL(1), HIGH(2), MEDIUM(3), LOW(4);
    
    private final int level;
    PatientPriority(int level) { this.level = level; }
    public int getLevel() { return level; }

}
