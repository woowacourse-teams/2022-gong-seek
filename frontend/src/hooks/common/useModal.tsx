import { useRecoilState } from 'recoil';

import { modalState } from '@/store/modalState';
import { RecoilStateInnerType } from '@/types/innerType';

const useModal = () => {
	const setModal = useRecoilState(modalState)[1];

	const showModal = (modalInfo: NonNullable<RecoilStateInnerType<typeof modalState>>) => {
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
