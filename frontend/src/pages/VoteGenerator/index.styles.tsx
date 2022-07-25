import { AiFillPlusCircle } from 'react-icons/ai';

import styled from '@emotion/styled';

export const Form = styled.form`
	width: 100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
`;

export const OptionInputBox = styled.div`
	display: flex;
	width: 50%;
	gap: ${({ theme }) => theme.size.SIZE_016};
	align-items: center;
`;

export const Content = styled.div`
	display: flex;
	flex-direction: column;
	width: 50%;

	gap: ${({ theme }) => theme.size.SIZE_014};
	margin-top: ${({ theme }) => theme.size.SIZE_026};
`;

export const SubmitButton = styled.button`
	color: ${({ theme }) => theme.colors.WHITE};

	background-color: ${({ theme }) => theme.colors.PURPLE_500};
	width: 80%;
	font-size: 0.8rem;
	padding: ${({ theme }) => theme.size.SIZE_004};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
	border-color: transparent;
	cursor: pointer;
	margin-top: ${({ theme }) => theme.size.SIZE_020};

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
	appearance: none;
	background: none;
	border: none;
	padding: 0;
`;
