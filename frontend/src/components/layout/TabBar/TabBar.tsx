import { useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import UserProfileIcon from '@/components/common/UserProfileIcon/UserProfileIcon';
import { UserProfile } from '@/components/common/UserProfileIcon/UserProfileIcon.styles';
import * as S from '@/components/layout/TabBar/TabBar.styles';
import { menuSliderState } from '@/store/menuSliderState';
import { getUserIsLogin } from '@/store/userState';

const TabBar = () => {
	const navigate = useNavigate();
	const isLogin = useRecoilValue(getUserIsLogin);

	const [sliderState, setSliderState] = useRecoilState(menuSliderState);

	return (
		<>
			<S.Section>
				<S.PostingLink
					onClick={() => {
						navigate('/category');
					}}
				/>
				{isLogin ? (
					<UserProfileIcon />
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
						setSliderState({ isOpen: true });
					}}
				/>
			</S.Section>
		</>
	);
};

export default TabBar;
