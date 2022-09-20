import { AiFillPlusCircle } from 'react-icons/ai';

import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;
	height: 60vh;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_016};
`;

export const AddOptionForm = styled.form`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;

	width: 100%;
`;

export const ContentForm = styled.form`
	display: flex;

	flex-direction: column;
	align-items: center;
	width: 100%;
	height: 100%;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: 60%;
		margin-top: ${({ theme }) => theme.size.SIZE_050};
	}
`;

export const OptionInputBox = styled.div`
	display: flex;
	justify-content: space-evenly;
	align-items: center;

	width: 90%;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: 50%;
	}
`;

export const Content = styled.div`
	display: flex;

	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_014};
	align-items: center;
	width: 90%;

	margin-top: ${({ theme }) => theme.size.SIZE_026};
`;

export const InputValidMessage = styled.p<{ isValid: boolean }>`
	color: ${({ theme, isValid }) => (isValid ? theme.colors.BLUE_500 : theme.colors.RED_500)};
	margin-top: ${({ theme }) => theme.size.SIZE_016};
`;

export const SubmitButton = styled.button`
	width: 60%;

	border-radius: ${({ theme }) => theme.size.SIZE_010};
	border-color: transparent;

	font-size: 0.8rem;

	color: ${({ theme }) => theme.colors.WHITE};
	background-color: ${({ theme }) => theme.colors.PURPLE_500};

	padding: ${({ theme }) => theme.size.SIZE_004};
	margin-top: ${({ theme }) => theme.size.SIZE_040};

	cursor: pointer;

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: 40%;
		padding: ${({ theme }) => theme.size.SIZE_008};
		font-size: ${({ theme }) => theme.size.SIZE_014};
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

	&:disabled ${AddButton} {
		cursor: not-allowed;
		color: ${({ theme }) => theme.colors.GRAY_500};
		opacity: 0.7;
	}
`;

export const RegisteredOptionTitle = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_018};
	margin-right: auto;
	padding-left: 10%;
	margin-top: ${({ theme }) => theme.size.SIZE_024};
`;
