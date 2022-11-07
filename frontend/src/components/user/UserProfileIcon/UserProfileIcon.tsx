import { Suspense, useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import Dropdown from '@/components/@common/Dropdown/Dropdown';
import * as S from '@/components/user/UserProfileIcon/UserProfileIcon.styles';
import useGetUserInfo from '@/hooks/user/useGetUserInfo';
import { dropdownState } from '@/store/dropdownState';

const UserProfileIcon = () => {
	const { data } = useGetUserInfo();
	const [dropdown, setDropdown] = useRecoilState(dropdownState);
	const [isLog, setIsLog] = useState(false);

	useEffect(() => {
		if (isLog) {
			setIsLog(false);
		}
	}, [isLog]);

	const handleUserProfileIconKeydown = (e: React.KeyboardEvent<HTMLDivElement>) => {
		if (e.key === 'Escape') {
			setIsLog(true);

			setDropdown({
				isOpen: false,
			});
		}
	};

	const handleClickUserProfile = () => {
		setDropdown((prev) => ({
			isOpen: !prev.isOpen,
		}));
	};

	return (
		<S.Container
			role="button"
			aria-label="드롭다운을 열려면 엔터를 누르세요"
			tabIndex={0}
			onClick={() =>
				setDropdown((prev) => ({
					isOpen: !prev.isOpen,
				}))
			}
			onKeyDown={handleUserProfileIconKeydown}
		>
			{
				<Suspense fallback={<S.UserProfile src={gongseek} alt="임시 작성자 프로필" />}>
					<S.UserProfile src={data?.avatarUrl} alt="작성자 프로필" />
				</Suspense>
			}
			{dropdown.isOpen && <Dropdown onCloseDropdown={handleClickUserProfile} />}
			{isLog && <S.SrOnlyContainer role="alert">닫힘</S.SrOnlyContainer>}
		</S.Container>
	);
};

export default UserProfileIcon;
