import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { URL } from '@/constants/url';
import { getUserIsLogin } from '@/store/userState';

const useDetailArticleState = () => {
	const [isCommentOpen, setIsCommentOpen] = useState(false);
	const isLogin = useRecoilValue(getUserIsLogin);
	const navigate = useNavigate();

	const handleCommentPlusButton = () => {
		if (isLogin) {
			setIsCommentOpen(true);
			return;
		}
		if (window.confirm('로그인이 필요한 서비스입니다 로그인 창으로 이동하시겠습니까? ')) {
			navigate(URL.LOGIN);
		}
	};

	return {
		handleCommentPlusButton,
		isLogin,
		isCommentOpen,
		setIsCommentOpen,
	};
};

export default useDetailArticleState;
