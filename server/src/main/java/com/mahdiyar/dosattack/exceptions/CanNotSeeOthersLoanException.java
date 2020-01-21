package com.mahdiyar.dosattack.exceptions;

import com.mahdiyar.dosattack.exceptions.handler.HandledException;
import com.mahdiyar.dosattack.exceptions.handler.ServiceException;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@HandledException("err.105")
public class CanNotSeeOthersLoanException extends ServiceException {
}
