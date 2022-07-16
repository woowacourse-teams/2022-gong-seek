import styled from '@emotion/styled';

const Input = styled.input<{ width?: string }>`
	width: ${({ width }) => width};
	height: ${({ theme }) => theme.size.SIZE_024};
	border-radius: ${({ theme }) => theme.size.SIZE_006};
	border-width: 1px;
	border-color: transparent;
	box-shadow: 1px 1px 1px ${({ theme }) => theme.boxShadows.primary};
	font-size: ${({ theme }) => theme.size.SIZE_012};
	padding: ${({ theme }) => theme.size.SIZE_006} ${({ theme }) => theme.size.SIZE_004};
	padding-left: ${({ theme }) => theme.size.SIZE_006};

	&::placeholder {
		font-size: ${({ theme }) => theme.size.SIZE_012};
		opacity: 0.6;
	}

	&:focus {
		outline-color: ${({ theme }) => theme.colors.PURPLE_500};
		outline-width: 1px;
	}

	&:disabled {
		background-color: ${({ theme }) => theme.colors.GRAY_500};
		opacity: 0.7;
	}
`;

export default Input;
