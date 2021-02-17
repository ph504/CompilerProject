.data
	newLine: 	.asciiz 	"\n"
	bool_0: 	.asciiz 	"false"
	bool_1: 	.asciiz 	"true"
	zeroDouble: 	.double 	0.0
	readLine_number0:	.space	20
.text
	ldc1	$f0, zeroDouble
	jal	main
PrintBool:
	beq	$a0, 0, Print_Bool0
	la	$v1, bool_1
	b	Print_Bool1
Print_Bool0:
	la	$v1, bool_0
Print_Bool1:
	jr	$ra
main:
	addi	$sp, $sp, -16
	sw	$a0, 0($sp)
	sw	$a1, 4($sp)
	sw	$a2, 8($sp)
	sw	$a3, 12($sp)
	addi	$sp, $sp, -16
	sw	$a0, 0($sp)
	sw	$a1, 4($sp)
	sw	$a2, 8($sp)
	sw	$a3, 12($sp)
	li	$v0, 8
	la	$a0, readLine_number0
	li	$a1, 20
	syscall
	move	$v1, $v0
	lw	$a0, 0($sp)
	lw	$a1, 4($sp)
	lw	$a2, 8($sp)
	lw	$a3, 12($sp)
	addi	$sp, $sp, 16
	li	$v0, 4
	move	$a0, $v1
	syscall
	li	$v0, 4
	la	$a0, newLine
	syscall
	# This line is going to signal end of program.
	li	$v0, 10
	syscall
