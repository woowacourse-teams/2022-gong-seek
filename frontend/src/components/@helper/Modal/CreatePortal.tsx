import { PropsWithStrictChildren } from 'gongseek-types';
import ReactDOM from 'react-dom';

import { ModalStateType } from '@/store/modalState';

type CreatePortalProps = {
	modalId: ModalStateType['modalType'] | 'snack-bar';
};

const CreatePortal = ({ modalId, children }: PropsWithStrictChildren<CreatePortalProps>) => {
	const modalElement = document.getElementById(`${modalId}`);
	if (modalElement === null) {
		throw new Error('모달을 찾을 수 없습니다.');
	}
	return ReactDOM.createPortal(children, modalElement);
};

export default CreatePortal;
