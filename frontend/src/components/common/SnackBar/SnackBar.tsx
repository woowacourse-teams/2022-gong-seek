import ReactDOM from 'react-dom'

import { useRecoilValue } from 'recoil';
import { snackBarState } from '@/store/snackBarState';

import * as S from '@/components/common/SnackBar/SnackBar.styles';


const SnackBar = () => {
    const snackBar = document.getElementById('snack-bar');
    const {isOpen, message} = useRecoilValue(snackBarState);

    if(!snackBar){
        throw new Error('스낵바를 찾지 못하였습니다');
    }
   
   return isOpen ? ReactDOM.createPortal(
    <S.Container>
        <h2 hidden>안내 메세지가 나오는 곳입니다</h2>
        <S.MessageBox>
            {message}
        </S.MessageBox>
    </S.Container>
   ,snackBar): null;
};

export default SnackBar;
