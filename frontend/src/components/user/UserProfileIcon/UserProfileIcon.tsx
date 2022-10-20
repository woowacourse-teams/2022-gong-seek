import { useRecoilState } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import Dropdown from '@/components/@common/Dropdown/Dropdown';
import * as S from '@/components/user/UserProfileIcon/UserProfileIcon.styles';
import useGetUserInfo from '@/hooks/user/useGetUserInfo';
import { dropdownState } from '@/store/dropdownState';

const UserProfileIcon = () => {
	const { data, isLoading, isSuccess } = useGetUserInfo();
	const [dropdown, setDropdown] = useRecoilState(dropdownState);

	const handleClickUserProfile = () => {
		setDropdown((prev) => ({
			isOpen: !prev.isOpen,
		}));
	};

	return (
		<S.Container>
			{isLoading && <S.UserProfile src={gongseek} />}
			{isSuccess && (
				<S.UserProfile
					src={data?.avatarUrl}
					alt="유저의 프로필 이미지"
					onClick={() =>
						setDropdown((prev) => ({
							isOpen: !prev.isOpen,
						}))
					}
				/>
			)}
			{dropdown.isOpen && <Dropdown onCloseDropdown={handleClickUserProfile} />}
		</S.Container>
	);
};

export default UserProfileIcon;
