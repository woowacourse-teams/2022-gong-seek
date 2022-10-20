import { useRecoilValue } from 'recoil';

import * as S from '@/components/@common/SnackBar/SnackBar.styles';
import CreatePortal from '@/components/@helper/Modal/CreatePortal';
import { snackBarState } from '@/store/snackBarState';

const SnackBar = () => {
	const { isOpen, message } = useRecoilValue(snackBarState);

	return (
		<CreatePortal modalId="snack-bar">
			{isOpen && (
				<S.Container>
					<h2 hidden>안내 메세지가 나오는 곳입니다</h2>
					<S.MessageBox>{message}</S.MessageBox>
				</S.Container>
			)}
		</CreatePortal>
	);
};

export default SnackBar;
