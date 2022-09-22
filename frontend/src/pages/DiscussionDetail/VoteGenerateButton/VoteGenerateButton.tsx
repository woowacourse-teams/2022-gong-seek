import styled from '@emotion/styled';

const VoteGenerateButton = styled.button`
	border-radius: ${({ theme }) => theme.size.SIZE_008};
	border-color: transparent;
	font-size: ${({ theme }) => theme.size.SIZE_014};

	width: 90%;

	background-color: ${({ theme }) => theme.colors.PURPLE_500};
	color: ${({ theme }) => theme.colors.WHITE};

	margin-top: ${({ theme }) => theme.size.SIZE_026};
	padding: ${({ theme }) => theme.size.SIZE_008} 0;

	cursor: pointer;

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		width: 30%;
		margin-top: ${({ theme }) => theme.size.SIZE_040};
		padding: ${({ theme }) => theme.size.SIZE_012} 0;
	}
`;

export default VoteGenerateButton;
