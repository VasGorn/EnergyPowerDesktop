package model;

public class WorkTable {
    private Employee employee;

    private WorkTable.columnDay  day1,  day2,  day3,  day4,  day5,  day6,  day7,  day8,  day9, day10,
                                day11, day12, day13, day14, day15, day16, day17, day18, day19, day20,
                                day21, day22, day23, day24, day25, day26, day27, day28, day29, day30,
                                day31;


    public WorkTable(Employee employee){
        this.employee = employee;
         day1 = new WorkTable.columnDay();  day2 = new WorkTable.columnDay();   day3 = new WorkTable.columnDay();
         day4 = new WorkTable.columnDay();  day5 = new WorkTable.columnDay();   day6 = new WorkTable.columnDay();
         day7 = new WorkTable.columnDay();  day8 = new WorkTable.columnDay();   day9 = new WorkTable.columnDay();
        day10 = new WorkTable.columnDay(); day11 = new WorkTable.columnDay();  day12 = new WorkTable.columnDay();
        day13 = new WorkTable.columnDay(); day14 = new WorkTable.columnDay();  day15 = new WorkTable.columnDay();
        day16 = new WorkTable.columnDay(); day17 = new WorkTable.columnDay();  day18 = new WorkTable.columnDay();
        day19 = new WorkTable.columnDay(); day20 = new WorkTable.columnDay();  day21 = new WorkTable.columnDay();
        day22 = new WorkTable.columnDay(); day23 = new WorkTable.columnDay();  day24 = new WorkTable.columnDay();
        day25 = new WorkTable.columnDay(); day26 = new WorkTable.columnDay();  day27 = new WorkTable.columnDay();
        day28 = new WorkTable.columnDay(); day29 = new WorkTable.columnDay();  day30 = new WorkTable.columnDay();
        day31 = new WorkTable.columnDay();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setHoursWorkType(int numDay, int workHours, int overHours, WorkType workType){
        switch(numDay){
            case 1:   day1.setWorkHours(workHours);  day1.setOverWorks(overHours);  day1.setWorkType(workType); break;
            case 2:   day2.setWorkHours(workHours);  day2.setOverWorks(overHours);  day2.setWorkType(workType); break;
            case 3:   day3.setWorkHours(workHours);  day3.setOverWorks(overHours);  day3.setWorkType(workType); break;
            case 4:   day4.setWorkHours(workHours);  day4.setOverWorks(overHours);  day4.setWorkType(workType); break;
            case 5:   day5.setWorkHours(workHours);  day5.setOverWorks(overHours);  day5.setWorkType(workType); break;
            case 6:   day6.setWorkHours(workHours);  day6.setOverWorks(overHours);  day6.setWorkType(workType); break;
            case 7:   day7.setWorkHours(workHours);  day7.setOverWorks(overHours);  day7.setWorkType(workType); break;
            case 8:   day8.setWorkHours(workHours);  day8.setOverWorks(overHours);  day8.setWorkType(workType); break;
            case 9:   day9.setWorkHours(workHours);  day9.setOverWorks(overHours);  day9.setWorkType(workType); break;
            case 10: day10.setWorkHours(workHours); day10.setOverWorks(overHours); day10.setWorkType(workType); break;
            case 11: day11.setWorkHours(workHours); day11.setOverWorks(overHours); day11.setWorkType(workType); break;
            case 12: day12.setWorkHours(workHours); day12.setOverWorks(overHours); day12.setWorkType(workType); break;
            case 13: day13.setWorkHours(workHours); day13.setOverWorks(overHours); day13.setWorkType(workType); break;
            case 14: day14.setWorkHours(workHours); day14.setOverWorks(overHours); day14.setWorkType(workType); break;
            case 15: day15.setWorkHours(workHours); day15.setOverWorks(overHours); day15.setWorkType(workType); break;
            case 16: day16.setWorkHours(workHours); day16.setOverWorks(overHours); day16.setWorkType(workType); break;
            case 17: day17.setWorkHours(workHours); day17.setOverWorks(overHours); day17.setWorkType(workType); break;
            case 18: day18.setWorkHours(workHours); day18.setOverWorks(overHours); day18.setWorkType(workType); break;
            case 19: day19.setWorkHours(workHours); day19.setOverWorks(overHours); day19.setWorkType(workType); break;
            case 20: day20.setWorkHours(workHours); day20.setOverWorks(overHours); day20.setWorkType(workType); break;
            case 21: day21.setWorkHours(workHours); day21.setOverWorks(overHours); day21.setWorkType(workType); break;
            case 22: day22.setWorkHours(workHours); day22.setOverWorks(overHours); day22.setWorkType(workType); break;
            case 23: day23.setWorkHours(workHours); day23.setOverWorks(overHours); day23.setWorkType(workType); break;
            case 24: day24.setWorkHours(workHours); day24.setOverWorks(overHours); day24.setWorkType(workType); break;
            case 25: day25.setWorkHours(workHours); day25.setOverWorks(overHours); day25.setWorkType(workType); break;
            case 26: day26.setWorkHours(workHours); day26.setOverWorks(overHours); day26.setWorkType(workType); break;
            case 27: day27.setWorkHours(workHours); day27.setOverWorks(overHours); day27.setWorkType(workType); break;
            case 28: day28.setWorkHours(workHours); day28.setOverWorks(overHours); day28.setWorkType(workType); break;
            case 29: day29.setWorkHours(workHours); day29.setOverWorks(overHours); day29.setWorkType(workType); break;
            case 30: day30.setWorkHours(workHours); day30.setOverWorks(overHours); day30.setWorkType(workType); break;
            case 31: day31.setWorkHours(workHours); day31.setOverWorks(overHours); day31.setWorkType(workType); break;
        }
    }

    public columnDay getDay1() {
        return day1;
    }

    public columnDay getDay2() {
        return day2;
    }

    public columnDay getDay3() {
        return day3;
    }

    public columnDay getDay4() {
        return day4;
    }

    public columnDay getDay5() {
        return day5;
    }

    public columnDay getDay6() {
        return day6;
    }

    public columnDay getDay7() {
        return day7;
    }

    public columnDay getDay8() {
        return day8;
    }

    public columnDay getDay9() {
        return day9;
    }

    public columnDay getDay10() {
        return day10;
    }

    public columnDay getDay11() {
        return day11;
    }

    public columnDay getDay12() {
        return day12;
    }

    public columnDay getDay13() {
        return day13;
    }

    public columnDay getDay14() {
        return day14;
    }

    public columnDay getDay15() {
        return day15;
    }

    public columnDay getDay16() {
        return day16;
    }

    public columnDay getDay17() {
        return day17;
    }

    public columnDay getDay18() {
        return day18;
    }

    public columnDay getDay19() {
        return day19;
    }

    public columnDay getDay20() {
        return day20;
    }

    public columnDay getDay21() {
        return day21;
    }

    public columnDay getDay22() {
        return day22;
    }

    public columnDay getDay23() {
        return day23;
    }

    public columnDay getDay24() {
        return day24;
    }

    public columnDay getDay25() {
        return day25;
    }

    public columnDay getDay26() {
        return day26;
    }

    public columnDay getDay27() {
        return day27;
    }

    public columnDay getDay28() {
        return day28;
    }

    public columnDay getDay29() {
        return day29;
    }

    public columnDay getDay30() {
        return day30;
    }

    public columnDay getDay31() {
        return day31;
    }

    public class columnDay{
        private int workHours;
        private int overWorks;
        private WorkType workType;

        public columnDay(){
            this.workHours = 0;
            this.overWorks = 0;
        }

        public columnDay(int workHours, int overWorkHours, WorkType workType){
            this.workHours = workHours;
            this.overWorks = overWorkHours;
            this.workType = workType;
        }

        public void setOverWorks(int overWorks) {
            this.overWorks = overWorks;
        }

        public void setWorkHours(int workHours){
            this.workHours = workHours;
        }

        public void setWorkType(WorkType workType){
            this.workType = workType;
        }

        @Override
        public String toString() {
            if (workHours < 1) {
                return "";
            } else if(workType == null){
                return String.valueOf(workHours) + " | " + String.valueOf(overWorks);
            } else {
                return String.valueOf(workHours) + " | " + String.valueOf(overWorks) + " | " + String.valueOf(workType.getId());
            }
        }

    }

}
