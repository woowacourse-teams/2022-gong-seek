import { PropsWithStrictChildren } from 'gongseek-types';
import ReactDOM from 'react-dom';

type ModalPortalProps = {
	modalId: string;
};

const ModalPortal = ({ modalId, children }: PropsWithStrictChildren<ModalPortalProps>) => {
	const modalElement = document.getElementById(`${modalId}`);
	if (modalElement === null) {
		throw new Error('모달을 찾을 수 없습니다.');
	}
	return ReactDOM.createPortal(children, modalElement);
};

export default ModalPortal;
