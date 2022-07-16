import styled from '@emotion/styled';

export const AddButton = styled.button<{ size?: string }>`
	width: ${({ size }) => size};
	height: ${({ size }) => size};
	background-color: ${({ theme }) => theme.colors.PURPLE_500};
	color: ${({ theme }) => theme.colors.WHITE};
	border-radius: 50%;
	border-color: transparent;
	font-size: ${({ theme }) => theme.size.SIZE_018};

	&:hover {
		filter: brightness(1.2);
		cursor: pointer;
	}

	&:disabled {
		background-color: ${({ theme }) => theme.colors.GRAY_500};
		opacity: 0.8;
	}
`;
