package employee.controller;

import employee.misc.events.Event;
import employee.misc.Observer;
import employee.model.Model;

public abstract class Controller implements Observer {

    protected Model mModel;

    public Controller(Model model, Event event){
        mModel = model;
        mModel.register(event, this);
    }

    public abstract void handleEvent(Event event, Object... params);

}
