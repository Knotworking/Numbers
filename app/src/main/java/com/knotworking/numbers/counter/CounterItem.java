package com.knotworking.numbers.counter;

public class CounterItem {
    private int id;
    private String name;
    private int count;

    public CounterItem() {
    }

    public CounterItem(int id, String name, int count) {
        this.id = id;
        this.count = count;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
//        if (this == o) return true;
//        if (!(o instanceof CollectionModel)) return false;
//
//        CollectionModel model = (CollectionModel) o;
//
//        if (getNumberOfQuestions() != model.getNumberOfQuestions()) return false;
//        if (getNumberOfCompletedQuestions() != model.getNumberOfCompletedQuestions()) return false;
//        if (questionsDownloaded != model.questionsDownloaded) return false;
//        if (picturesDownloaded != model.picturesDownloaded) return false;
//        if (Double.compare(model.getTotalPoints(), getTotalPoints()) != 0) return false;
//        if (correctlyAnswered != model.correctlyAnswered) return false;
//        if (!getTitle().equals(model.getTitle())) return false;
//        return getId().equals(model.getId());

        if (this == obj) return true;
        if (!(obj instanceof CounterItem)) return false;

        CounterItem item = (CounterItem)obj;

        if (!name.equals(item.getName())) return false;
        if (count != item.getCount()) return false;
        return id == item.getId();





//        return super.equals(obj);
    }
}
