import { useRecoilState } from 'recoil';

import { modalState } from '@/store/modalState';
import { GetInsideRecoilState } from '@/types/util';

const useModal = () => {
	const setModal = useRecoilState(modalState)[1];

	const showModal = (modalInfo: NonNullable<GetInsideRecoilState<typeof modalState>>) => {
		setModal(modalInfo);
	};

	const hideModal = () => {
		setModal(null);
	};

	return {
		showModal,
		hideModal,
	};
};

export default useModal;
