import { convertGithubAvartarUrlForResize } from '../../../utils/converter';
import React, { useEffect, useState } from 'react';

import Loading from '@/components/common/Loading/Loading';
import usePutUserProfile from '@/hooks/user/usePutUserProfile';
import { queryClient } from '@/index';
import * as S from '@/pages/MyPage/UserProfile/UserProfile.styles';
import { validatedEditInput } from '@/utils/validateInput';

export interface UserProfileProps {
	name: string;
	avatarUrl: string;
}

const UserProfile = ({ name, avatarUrl }: UserProfileProps) => {
	const [isEdit, setIsEdit] = useState(false);
	const [editedName, setEditedName] = useState(name);
	const { data, isLoading, isSuccess, onClickConfirmButton } = usePutUserProfile();
	const isValidInput = validatedEditInput(editedName);

	const onClickEditIcon = () => {
		setIsEdit(true);
	};

	useEffect(() => {
		if (isSuccess) {
			queryClient.invalidateQueries('user-info');
		}
	}, [isSuccess]);

	if (isLoading) return <Loading />;

	return (
		<S.Container>
			<S.UserProfile src={convertGithubAvartarUrlForResize(avatarUrl)} />
			<S.UserNameBox>
				{isEdit ? (
					<S.UserNameContainer>
						<S.EditUserNameBox>
							<S.EditUserNameInput
								value={editedName}
								onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
									setEditedName(e.target.value);
								}}
							/>
							<S.ConfirmButton
								disabled={!isValidInput}
								onClick={() => {
									onClickConfirmButton({ name: editedName });
									setIsEdit(false);
								}}
							>
								수정
							</S.ConfirmButton>
						</S.EditUserNameBox>

						<S.ValidateMessage isValid={isValidInput}>
							{isValidInput ? '유효한 입력입니다.' : '다시 입력해주세요'}
						</S.ValidateMessage>
					</S.UserNameContainer>
				) : (
					<S.UserName>{name}</S.UserName>
				)}
				{!isEdit && <S.EditIcon onClick={onClickEditIcon} />}
			</S.UserNameBox>
		</S.Container>
	);
};

export default UserProfile;
