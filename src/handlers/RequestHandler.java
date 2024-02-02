package handlers;

import Exceptions.InvalidDataException;
import pojos.Session;

public interface RequestHandler {
    public Object handle(Object request, Session session) throws InvalidDataException;
}
