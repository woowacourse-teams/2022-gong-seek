import { AxiosError, AxiosResponse } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useRecoilState } from 'recoil';

import { editUserInfo } from '@/api/myPage';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { errorPortalState } from '@/store/errorPortalState';

const usePutUserProfile = () => {
	const { data, isSuccess, isLoading, isError, error, mutate } = useMutation<
		AxiosResponse<{ name: string }>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ name: string }
	>(editUserInfo, { retry: 1 });
	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);

	useEffect(() => {
		if (isError) {
			if (!error.response?.data?.errorCode) {
				setErrorPortal({ isOpen: true });
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response?.data.errorCode],
			);
		}
	}, [isError]);

	const onClickConfirmButton = ({ name }: { name: string }) => {
		mutate({ name });
	};

	return { onClickConfirmButton, isLoading, isSuccess, data };
};

export default usePutUserProfile;
