import { AiFillPlusCircle } from 'react-icons/ai';

import styled from '@emotion/styled';

export const Form = styled.form`
	display: flex;

	flex-direction: column;
	justify-content: center;
	align-items: center;

	width: 100%;
`;

export const OptionInputBox = styled.div`
	display: flex;

	gap: ${({ theme }) => theme.size.SIZE_016};
	align-items: center;

	width: 50%;
`;

export const Content = styled.div`
	display: flex;

	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_014};

	width: 50%;

	margin-top: ${({ theme }) => theme.size.SIZE_026};
`;

export const SubmitButton = styled.button`
	width: 80%;

	border-radius: ${({ theme }) => theme.size.SIZE_010};
	border-color: transparent;

	font-size: 0.8rem;

	color: ${({ theme }) => theme.colors.WHITE};
	background-color: ${({ theme }) => theme.colors.PURPLE_500};

	padding: ${({ theme }) => theme.size.SIZE_004};
	margin-top: ${({ theme }) => theme.size.SIZE_020};

	cursor: pointer;

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;

export const AddButton = styled(AiFillPlusCircle)`
	width: ${({ theme }) => theme.size.SIZE_032};
	height: ${({ theme }) => theme.size.SIZE_032};

	color: ${({ theme }) => theme.colors.PURPLE_500};

	cursor: pointer;

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;

export const AddButtonWrapper = styled.button`
	border: none;

	padding: 0;

	appearance: none;
	background: none;
`;
