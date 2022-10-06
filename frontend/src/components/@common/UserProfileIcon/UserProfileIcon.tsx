import { useRecoilState } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import Dropdown from '@/components/@common/Dropdown/Dropdown';
import * as S from '@/components/@common/UserProfileIcon/UserProfileIcon.styles';
import useGetUserInfo from '@/hooks/user/useGetUserInfo';
import { dropdownState } from '@/store/dropdownState';

const UserProfileIcon = () => {
	const { data, isLoading, isSuccess } = useGetUserInfo();
	const [dropdown, setDropdown] = useRecoilState(dropdownState);

	return (
		<S.Container>
			{isLoading && <S.UserProfile src={gongseek} />}
			{isSuccess && (
				<S.UserProfile
					src={data?.avatarUrl}
					onClick={() =>
						setDropdown((prev) => ({
							isOpen: !prev.isOpen,
						}))
					}
				/>
			)}
			{dropdown.isOpen && <Dropdown onClickCloseDropdown={() => setDropdown({ isOpen: false })} />}
		</S.Container>
	);
};

export default UserProfileIcon;
