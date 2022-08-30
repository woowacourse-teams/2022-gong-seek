import { useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import Loading from '@/components/common/Loading/Loading';
import { UserProfile } from '@/components/common/UserProfileIcon/UserProfileIcon.styles';
import * as S from '@/components/layout/TabBar/TabBar.styles';
import useGetUserInfo from '@/pages/MyPage/hooks/useGetUserInfo';
import { menuSliderState } from '@/store/menuSliderState';
import { getUserIsLogin } from '@/store/userState';

const TabBar = () => {
	const navigate = useNavigate();
	const isLogin = useRecoilValue(getUserIsLogin);

	const [sliderState, setSliderState] = useRecoilState(menuSliderState);
	const { data, isLoading } = useGetUserInfo();

	if (isLoading) return <Loading />;

	return (
		<>
			<S.Section>
				<S.PostingLink
					onClick={() => {
						navigate('/category');
					}}
				/>
				{isLogin ? (
					<UserProfile src={data?.avatarUrl} onClick={() => navigate('/my-page')} />
				) : (
					<S.UserCircleLink
						onClick={() => {
							navigate('/my-page');
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
