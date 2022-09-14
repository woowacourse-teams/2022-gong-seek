import { ReactNode } from 'react';
import ReactDOM from 'react-dom';
import { useRecoilValue } from 'recoil';

import * as S from '@/components/common/SnackBar/SnackBar.styles';
import { snackBarState } from '@/store/snackBarState';

const SnackBarPortal = ({ children }: { children: ReactNode }) => {
	const snackBar = document.getElementById('snack-bar');

	if (!snackBar) {
		throw new Error('스낵바를 찾지 못하였습니다');
	}

	return ReactDOM.createPortal(<div>{children}</div>, snackBar);
};

const SnackBar = () => {
	const { isOpen, message } = useRecoilValue(snackBarState);

	return (
		<SnackBarPortal>
			{isOpen && (
				<S.Container>
					<h2 hidden>안내 메세지가 나오는 곳입니다</h2>
					<S.MessageBox>{message}</S.MessageBox>
				</S.Container>
			)}
		</SnackBarPortal>
	);
};

export default SnackBar;
