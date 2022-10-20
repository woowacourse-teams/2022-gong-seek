import React, { useEffect, useState } from 'react';

import Loading from '@/components/@common/Loading/Loading';
import * as S from '@/components/user/UserProfile/UserProfile.styles';
import useSnackBar from '@/hooks/common/useSnackBar';
import usePutUserProfile from '@/hooks/user/usePutUserProfile';
import { queryClient } from '@/index';
import { convertGithubAvatarUrlForResize } from '@/utils/converter';
import { validatedUserNameInput } from '@/utils/validateInput';

export interface UserProfileProps {
	name: string;
	avatarUrl: string;
}

const UserProfile = ({ name, avatarUrl }: UserProfileProps) => {
	const [isEdit, setIsEdit] = useState(false);
	const [editedName, setEditedName] = useState(name);
	const { isLoading, isSuccess, handleClickUserConfirmButton } = usePutUserProfile();
	const isValidInput = validatedUserNameInput(editedName);
	const { showSnackBar } = useSnackBar();

	const handleClickUserNameEditButton = () => {
		setIsEdit(true);
	};

	const handleChangeUserNameEditInput = (e: React.ChangeEvent<HTMLInputElement>) => {
		setEditedName(e.target.value);
	};

	const handleClickUserNameConfirmButton = ({ editedName }: { editedName: string }) => {
		if (validatedUserNameInput(editedName)) {
			handleClickUserConfirmButton({ name: editedName });
			setIsEdit(false);
			return;
		}
		showSnackBar('유저의 이름은 최소 한글자 이상 255자 미만이여야 합니다');
	};

	useEffect(() => {
		if (isSuccess) {
			queryClient.invalidateQueries('user-info');
		}
	}, [isSuccess]);

	if (isLoading) return <Loading />;

	return (
		<S.Container>
			<S.UserProfile src={convertGithubAvatarUrlForResize(avatarUrl)} />
			<S.UserNameBox>
				{isEdit ? (
					<S.UserNameContainer>
						<S.EditUserNameBox>
							<S.EditUserNameInput
								value={editedName}
								onChange={handleChangeUserNameEditInput}
								minLength={1}
								maxLength={255}
							/>
							<S.ConfirmButton
								disabled={!isValidInput}
								onClick={() => {
									handleClickUserNameConfirmButton({ editedName });
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
				{!isEdit && <S.EditIcon onClick={handleClickUserNameEditButton} />}
			</S.UserNameBox>
		</S.Container>
	);
};

export default UserProfile;
