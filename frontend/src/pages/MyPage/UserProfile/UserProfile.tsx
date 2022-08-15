import React, { useEffect, useState } from 'react';

import Loading from '@/components/common/Loading/Loading';
import { queryClient } from '@/index';
import * as S from '@/pages/MyPage/UserProfile/UserProfile.styles';
import usePutUserProfile from '@/pages/MyPage/UserProfile/hooks/usePutUserProfile';

export interface UserProfileProps {
	name: string;
	avatarUrl: string;
}

const UserProfile = ({ name, avatarUrl }: UserProfileProps) => {
	const [isEdit, setIsEdit] = useState(false);
	const [editedName, setEditedName] = useState(name);
	const { data, isLoading, isSuccess, onClickConfirmButton } = usePutUserProfile();
	const onClickEditIcon = () => {
		setIsEdit(true);
	};

	const onChangeNameInput = (e: React.ChangeEvent<HTMLInputElement>) => {
		setEditedName(e.target.value);
	};

	useEffect(() => {
		if (isSuccess) {
			queryClient.invalidateQueries('user-info');
		}
	}, [isSuccess]);

	if (isLoading) return <Loading />;

	return (
		<S.Container>
			<S.UserProfile src={avatarUrl} />
			<S.UserNameBox>
				{isEdit ? (
					<S.EditUserNameInput value={editedName} onChange={onChangeNameInput} />
				) : (
					<S.UserName>{name}</S.UserName>
				)}
				{isEdit ? (
					<S.ConfirmIcon
						size={20}
						onClick={() => {
							onClickConfirmButton({ name: editedName });
						}}
					/>
				) : (
					<S.EditIcon onClick={onClickEditIcon} />
				)}
			</S.UserNameBox>
		</S.Container>
	);
};

export default UserProfile;
