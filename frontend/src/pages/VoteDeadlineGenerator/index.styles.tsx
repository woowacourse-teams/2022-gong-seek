import styled from '@emotion/styled';

export const Container = styled.form`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_056};

	:invalid button {
		pointer-events: none;
		background-color: ${({ theme }) => theme.colors.GRAY_500};
	}
`;

export const VoteDeadlineLabel = styled.label`
	font-size: ${({ theme }) => theme.size.SIZE_014};
`;

export const VoteDeadlineInputBox = styled.div`
	width: 70%;

	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;

	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const DeadlineInput = styled.input`
	width: 100%;

	border-radius: ${({ theme }) => theme.size.SIZE_010};
	padding: ${({ theme }) => theme.size.SIZE_006} ${({ theme }) => theme.size.SIZE_004};
	border-color: ${({ theme }) => theme.colors.PURPLE_500};
	::-webkit-datetime-edit-text {
		color: ${({ theme }) => theme.colors.BLUE_500};
	}

	:invalid + span::after {
		content: '유효하지 않은 입력입니다.';
		color: ${({ theme }) => theme.colors.RED_500};
		font-size: ${({ theme }) => theme.size.SIZE_012};
	}

	:valid + span::after {
		content: '등록이 가능합니다.';
		color: ${({ theme }) => theme.colors.BLUE_500};
		font-size: ${({ theme }) => theme.size.SIZE_012};
	}
`;

export const ValidateMessage = styled.span`
	margin-right: auto;
`;

export const SubmitButton = styled.button`
	width: 70%;
	height: fit-content;

	border-radius: ${({ theme }) => theme.size.SIZE_010};
	border-color: transparent;

	font-size: 0.8rem;

	color: ${({ theme }) => theme.colors.WHITE};
	background-color: ${({ theme }) => theme.colors.PURPLE_500};

	padding: ${({ theme }) => theme.size.SIZE_004};
	margin-top: ${({ theme }) => theme.size.SIZE_050};

	cursor: pointer;

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: ${({ theme }) => theme.size.SIZE_100};
		height: ${({ theme }) => theme.size.SIZE_040};

		font-size: ${({ theme }) => theme.size.SIZE_016};

		margin-left: auto;
	}
`;
