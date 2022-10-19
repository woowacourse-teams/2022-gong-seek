import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { URL } from '@/constants/url';
import useModal from '@/hooks/common/useModal';
import { getUserIsLogin } from '@/store/userState';

interface useDetailCommentStateProp {
	articleId: string;
}

const useDetailCommentState = ({ articleId }: useDetailCommentStateProp) => {
	const [tempSavedComment, setTempSavedComment] = useState('');
	const { showModal } = useModal();

	const isLogin = useRecoilValue(getUserIsLogin);
	const navigate = useNavigate();

	const handleClickCommentPlusButton = () => {
		if (isLogin) {
			showModal({
				modalType: 'comment-modal',
				modalProps: {
					articleId,
					modalType: 'register',
					placeholder: tempSavedComment,
					setTempSavedComment,
				},
				isMobileOnly: false,
			});
			return;
		}
		if (window.confirm('로그인이 필요한 서비스입니다 로그인 창으로 이동하시겠습니까? ')) {
			navigate(URL.LOGIN);
		}
	};

	return {
		handleClickCommentPlusButton,
		isLogin,
		tempSavedComment,
		setTempSavedComment,
	};
};

export default useDetailCommentState;
