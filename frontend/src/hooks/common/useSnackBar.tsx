import { useRecoilState } from 'recoil';

import { snackBarState } from '@/store/snackBarState';

const useSnackBar = () => {
	//TODO: 안쓰는 snackBar state 제거
	const [snackBar, setSnackBar] = useRecoilState(snackBarState);

	const showSnackBar = (message: string) => {
		setSnackBar({ isOpen: true, message: message });
		setTimeout(() => {
			setSnackBar({ isOpen: false, message: message });
		}, 1500);
	};

	return { showSnackBar };
};

export default useSnackBar;
