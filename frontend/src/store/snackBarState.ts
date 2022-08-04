import {atom} from 'recoil';

export const snackBarState = atom({
    key: 'snackBarState',
    default :
        {isOpen: false,
        message: ''}, 
})