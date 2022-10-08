import { useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import * as S from '@/components/@layout/TabBar/TabBar.styles';
import UserProfileIcon from '@/components/user/UserProfileIcon/UserProfileIcon';
import { UserProfile } from '@/components/user/UserProfileIcon/UserProfileIcon.styles';
import { URL } from '@/constants/url';
import useModal from '@/hooks/common/useModal';
import { menuSliderState } from '@/store/menuSliderState';
import { getUserIsLogin } from '@/store/userState';

const TabBar = () => {
	const navigate = useNavigate();
	const isLogin = useRecoilValue(getUserIsLogin);

	const [sliderState, setSliderState] = useRecoilState(menuSliderState);
	const { showModal } = useModal();

	return (
		<S.Section>
			<S.PostingLink
				onClick={() => {
					navigate(URL.CATEGORY_SELECTOR);
				}}
			/>
			{isLogin ? (
				<S.MyPageLink to={URL.MY_PAGE}>
					<UserProfileIcon />
				</S.MyPageLink>
			) : (
				<UserProfile
					src={gongseek}
					onClick={() => {
						navigate('/login');
					}}
				/>
			)}
			<S.MenuLink
				onClick={() => {
					showModal({ modalType: 'menu-slider', modalProps: {} });
				}}
			/>
		</S.Section>
	);
};

export default TabBar;
