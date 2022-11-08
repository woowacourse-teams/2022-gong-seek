import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import * as S from '@/components/@layout/TabBar/TabBar.styles';
import UserProfileIcon from '@/components/user/UserProfileIcon/UserProfileIcon';
import { UserProfile } from '@/components/user/UserProfileIcon/UserProfileIcon.styles';
import { URL } from '@/constants/url';
import useModal from '@/hooks/common/useModal';
import { getUserIsLogin } from '@/store/userState';

const TabBar = () => {
	const navigate = useNavigate();
	const isLogin = useRecoilValue(getUserIsLogin);

	const { showModal } = useModal();

	const handleClickPostingLink = () => {
		navigate(URL.CATEGORY_SELECTOR);
	};

	const handleClickUserProfile = () => {
		navigate(URL.LOGIN);
	};

	const handleOpenSlider = () => {
		showModal({ modalType: 'menu-slider', modalProps: {}, isMobileOnly: true });
	};

	return (
		<S.Section>
			<S.PostingLink onClick={handleClickPostingLink} />
			{isLogin ? (
				<S.MyPageLink to={URL.MY_PAGE}>
					<UserProfileIcon />
				</S.MyPageLink>
			) : (
				<UserProfile src={gongseek} alt="공식 이미지" onClick={handleClickUserProfile} />
			)}
			<S.MenuBox onClick={handleOpenSlider}>
				<S.MenuLink />
			</S.MenuBox>
		</S.Section>
	);
};

export default TabBar;
